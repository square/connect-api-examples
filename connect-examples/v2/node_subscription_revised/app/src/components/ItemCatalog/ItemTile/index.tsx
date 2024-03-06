
import { Card, CustomFlowbiteTheme, Flowbite } from 'flowbite-react';
import React, { useContext, useEffect } from 'react';
import { Item } from '..';
import ComponentLayout from '../../ComponentLayout';
import { AppContext, AppDispatchContext } from '../../../context/AppContext';

interface ItemTileProps {
    item: Item;
    isActionable: boolean;
}

const customTheme: CustomFlowbiteTheme = {
  card: {
    root: {
      children: 'flex h-full flex-col justify-start gap-4 p-6'
    }
  },
};


const ItemTile : React.FC<ItemTileProps> = ({ item, isActionable }) => {
  const { selectedItems } = useContext(AppContext);
  const dispatch = useContext(AppDispatchContext);

  useEffect(() => {
    if (selectedItems.length === 2) {
      dispatch({ type: 'SET_NEXT_STATE', payload: false })
    } else {
      dispatch({ type: 'SET_NEXT_STATE', payload: true})
    }
  }, [selectedItems]);

  const onHandleItemSelection = (item: Item) => {
    if (selectedItems.includes(item)) {
      // Remove item from selectedItems
      const updatedItems = selectedItems.filter((selectedItem: Item) => selectedItem.id !== item.id);
      dispatch({ type: 'SET_ITEMS', payload: updatedItems });
    } else if (selectedItems.length < 2) {
      // Add item to selectedItems
      const updatedItems = [...selectedItems, item];
      dispatch({ type: 'SET_ITEMS', payload: updatedItems });
    } else {
      // Remove the first item from selectedItems
      const updatedItems = [...selectedItems.slice(1), item];
      dispatch({ type: 'SET_ITEMS', payload: updatedItems });
    }
  };

  // Extracting price data
  const priceInCents = item.itemData.variations[0].itemVariationData.priceMoney.amount;

  // Function to convert cents to dollars
  const centsToDollars = (cents: number) => (cents / 100).toFixed(2);

  // Calculate discounted price (10% off)
  const discountedPrice = ((priceInCents * 0.9)/100).toFixed(2);


  return (
    <Flowbite theme={{theme: customTheme}}>
    <Card
      onClick={isActionable ?  () => onHandleItemSelection(item) : () => {}}
      className={`max-w-xs mb-4 mr-4 ${isActionable ? `cursor-pointer ${selectedItems.find((selectedItem: Item) => selectedItem.id === item.id) ? 'bg-blue-200' : ''}`: ``}`}
      renderImage={(props) => {
        return (
          <img
            {...props}
            className="object-cover w-full h-48"
            src={item.image}
            alt={item.itemData.name}
          />
        );
      }}
    >
      <div className="flex flex-col items-start align-start">
      <ComponentLayout title="src/components/ItemCatalog/ItemTile/index.tsx">
        <h5 className="text-2xl font-bold tracking-tight text-gray-900 dark:text-white">
          {item.itemData.name}
        </h5>
        {
          isActionable ?
          <p className="font-normal text-gray-700 dark:text-gray-400">
              {item.itemData.description}
            </p> 
            :
            null
        }
        <div>
          <p>
            <span className='text-gray-500 mr-2 font-bold'>${centsToDollars(priceInCents)}</span>
          </p>
        </div>
        </ComponentLayout>
      </div>
    </Card>
    </Flowbite>
  );
}

export default ItemTile

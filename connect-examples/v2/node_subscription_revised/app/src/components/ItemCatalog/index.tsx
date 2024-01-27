import React, { useState, useEffect } from 'react';
import ItemTile from './ItemTile';

export interface Item {
  id: string;
  image: string;
  itemData: {
    name: string;
    description: string;
    imageIds: string[];
    variations: Item[];
  };
}

interface ItemCatalogProps {
  categoryIds: string[];
  setIsNextDisabled: (isNextDisabled: boolean) => void;
  setSelectedItems: React.Dispatch<React.SetStateAction<Item[]>>;
  selectedItems: Item[];
}

const ItemCatalog: React.FC<ItemCatalogProps> = ({categoryIds, setIsNextDisabled, setSelectedItems, selectedItems}) => {

    const [itemCatalog, setItemCatalog] = useState<Item[]>([]);

    useEffect(() => {
        const fetchData = async () => {
          try {
            const response = await fetch('/catalog_items', {
              method: 'POST',
              headers: {
                'Content-Type': 'application/json'
              },
              body: JSON.stringify({
                categoryIds: categoryIds
              })
            });
            const data = await response.json();
            setItemCatalog(data);
          } catch (error) {
            console.error('Error fetching customer data:', error);
          }
        };
    
        fetchData();
      }, [categoryIds]); // Empty dependency array ensures the effect runs once when the component mounts

      const onHandleItemSelection = (item: any) => {
        setSelectedItems((prevSelectedItems) => {
          if (prevSelectedItems.includes(item)) {
            // Remove item from selectedItems
            return prevSelectedItems.filter((selectedItem) => selectedItem.id !== item.id);
          } else if (prevSelectedItems.length < 2) {
            // Add item to selectedItems
            return [...prevSelectedItems, item];
          } else {
            // Remove the first item from selectedItems
            return [...prevSelectedItems.slice(1), item];
          }
        });
      };

      return (
        <>
        {itemCatalog?.map((item, i) => (
            <ItemTile key={i} item={item} onHandleItemSelection={onHandleItemSelection} selectedItems={selectedItems} setIsNextDisabled={setIsNextDisabled}/>
          ))}
          </>
      );
};

export default ItemCatalog;

import { Card } from "flowbite-react";
import React, { useEffect } from "react";
import { Item } from "..";

interface ItemTileProps {
  item: Item;
  selectedItems: Item[];
  setIsNextDisabled: (isNextDisabled: boolean) => void;
  onHandleItemSelection: ((item: Item) => void) | null;
}

const ItemTile: React.FC<ItemTileProps> = ({
  item,
  onHandleItemSelection,
  selectedItems,
  setIsNextDisabled,
}) => {
  useEffect(() => {
    if (selectedItems.length === 2) {
      setIsNextDisabled(false);
    } else {
      setIsNextDisabled(true);
    }
  }, [selectedItems, setIsNextDisabled]);

  return (
    <Card
      onClick={
        onHandleItemSelection ? () => onHandleItemSelection(item) : () => {}
      }
      className={`max-w-sm mb-4 mr-4 border-gray-30 ${
        onHandleItemSelection ? `cursor-pointer` : ``
      } ${
        selectedItems.find((selectedItem: Item) => selectedItem.id === item.id)
          ? "border-solid border-blue"
          : ""
      }`}
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
      <h5 className="text-2xl font-bold tracking-tight text-gray-10">
        {item.itemData.name}
      </h5>
      <p className="font-normal text-gray-10">{item.itemData.description}</p>
    </Card>
  );
};

export default ItemTile;

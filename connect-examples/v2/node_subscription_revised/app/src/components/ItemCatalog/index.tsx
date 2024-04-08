import React, { useState, useEffect, useContext } from 'react';
import ItemTile from './ItemTile';
import { Card } from 'flowbite-react';
import Skeleton from '../Skeleton';
import { AppContext } from '../../context/AppContext';

export interface ItemVariation {
  type: string;
  id: string;
  updatedAt: string;
  version: string;
  isDeleted: boolean;
  presentAtAllLocations: boolean;
  itemVariationData: {
    name: string;
    priceMoney: {
      amount: number;
      currency: string;
    }
  };
}

export interface Item {
  id: string;
  image: string;
  itemData: {
    name: string;
    description: string;
    imageIds: string[];
    variations: ItemVariation[];
  };
}

interface ItemCatalogProps {
}

const ItemCatalog: React.FC<ItemCatalogProps> = () => {

    const [itemCatalog, setItemCatalog] = useState<Item[]>([]);
    const [isLoading, setIsLoading] = useState<boolean>(true);

    const {
      eligibleCategoryIds,
    } = useContext(AppContext);

 
    useEffect(() => {
        const fetchData = async () => {
          try {
            const response = await fetch('/catalog', {
              method: 'POST',
              headers: {
                'Content-Type': 'application/json'
              },
              body: JSON.stringify({
                categoryIds: eligibleCategoryIds
              })
            });
            const data = await response.json();
            setItemCatalog(data);
            setIsLoading(false);
          } catch (error) {
            console.error('Error fetching customer data:', error);
          }
        };
    
        fetchData();
      }, [eligibleCategoryIds]); // Empty dependency array ensures the effect runs once when the component mounts
    

      return (
        <>
        {isLoading ? <Card>
            <Skeleton/>
        </Card>:
        itemCatalog?.map((item, i) => (
            <ItemTile key={i} item={item} isActionable={true}/>
          ))}
          </>
      );
};

export default ItemCatalog;

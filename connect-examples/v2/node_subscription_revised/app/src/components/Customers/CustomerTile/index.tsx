import { Card, Avatar } from "flowbite-react";
import { CustomerData } from "..";
import { useNavigate } from "react-router-dom";

interface CustomerTileProps {
  customer: CustomerData;
  selectedCustomerId: string | null;
  handleCustomerSelection: ((customer: CustomerData) => void) | null;
}

const CustomerTile: React.FC<CustomerTileProps> = ({
  customer,
  selectedCustomerId,
  handleCustomerSelection,
}) => {
  const isSelected = selectedCustomerId === customer.id;
  const navigate = useNavigate();
  return (
    <Card
      className={`max-w-sm mr-4 cursor-pointer border-gray-30 ${
        isSelected ? "border-blue" : ""
      } ${handleCustomerSelection ? "" : "cursor-default hover:bg-white"}`}
      onClick={
        handleCustomerSelection
          ? () => handleCustomerSelection(customer)
          : undefined
      }
    >
      <div className="flex items-center mb-4">
        <Avatar rounded className="pr-4" />
        <div>
          <h5 className="text-2xl font-bold tracking-tight text-black">
            {`${customer.givenName} ${customer.familyName}`}
          </h5>
          <p className="font-normal text-black">
            {`Email: ${customer.emailAddress}`}
          </p>
        </div>
      </div>
      <div>
        {customer.cards?.length ? (
          <>
            <h4 className="text-lg font-semibold mb-2">Card Details:</h4>
            {customer.cards.map((card, i) => (
              <ul key={i}>
                <li>{`${card.cardBrand} - Ending in ${card.last4}`}</li>
                <li>{`Exp: ${card.expMonth}/${card.expYear}`}</li>
              </ul>
            ))}
          </>
        ) : (
          <h4 className="text-lg font-semibold mb-2">No card on file</h4>
        )}
      </div>
      <button
        type="button"
        className={`w-full rounded-lg bg-cyan-600 px-5 py-2.5 text-center text-sm font-medium text-black hover:bg-cyan-700 focus:outline-none focus:ring-4 focus:ring-gray-30 ${
          isSelected ? "mt-4" : ""
        }`}
        onClick={() => navigate(`/customer/${customer.id}`)}
      >
        Manage User Subscriptions
      </button>
    </Card>
  );
};

export default CustomerTile;

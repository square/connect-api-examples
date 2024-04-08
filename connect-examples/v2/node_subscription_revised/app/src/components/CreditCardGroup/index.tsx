import { useContext } from "react";
import { AppContext } from "../../context/AppContext";
import CardsOnFile from "./CardsOnFile";
import NewCreditCardInput from "./NewCreditCardInput";
import ComponentLayout from "../ComponentLayout";

interface CreditCardGroupProps {}

const CreditCardGroup: React.FC<CreditCardGroupProps> = () => {
    const { selectedCustomer } = useContext(AppContext)
    return <ComponentLayout title={"src/components/CreditCardGroup/index.tsx"}>
    {selectedCustomer?.cards?.length ?
        <div className="flex flex-col items-center">
            <CardsOnFile/> 
            <h1 className="mb-4 font-semibold text-md">OR</h1>
            <NewCreditCardInput/>
        </div> :
        <div className="flex items-start">
            <NewCreditCardInput/>
        </div>
    }
    </ComponentLayout>
}

export default CreditCardGroup;
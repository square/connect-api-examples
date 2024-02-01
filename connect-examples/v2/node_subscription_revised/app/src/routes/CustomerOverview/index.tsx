import { useState } from "react";
import SubscriptionModal from "./SubscriptionModal";
import SubscriptionsTable from "./SubscriptionsTable";
import { useParams } from 'react-router-dom';


interface CustomerOverviewProps {
}

const CustomerOverview: React.FC<CustomerOverviewProps> = () => {
    const { customerId } = useParams();
    const [openModal, setOpenModal] = useState(false);

    if (!customerId) {
        return <div>Missing customer ID</div>
    }

    return <div className="flex flex-col items-center">
        <h1>Account Overview: {customerId}</h1>
        <SubscriptionsTable customerId={customerId} setOpenModal={setOpenModal}/>
        <SubscriptionModal setOpenModal={setOpenModal} openModal={openModal}/>
    </div>
}

export default CustomerOverview;
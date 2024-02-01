import { Button, Modal } from 'flowbite-react';

interface SubscriptionModalProps {
    openModal: boolean,
    setOpenModal: (openModal: boolean) => void
}


const SubscriptionModal: React.FC<SubscriptionModalProps> = ({
    openModal,
    setOpenModal
}) =>{
  return (
    <>
      <Modal dismissible show={openModal} onClose={() => setOpenModal(false)}>
        <Modal.Header>Manage Subscription</Modal.Header>
        <Modal.Body>
          <div className="flex">
            <Button
                className='mr-2'
                color="red" 
                onClick={() => setOpenModal(false)}>
                Cancel Subscription
            </Button>
            <Button 
                color="yellow" 
                onClick={() => setOpenModal(false)}>
                Pause Subscription
            </Button>
          </div>
          <div className="space-y-6">
            <p className="text-base leading-relaxed text-gray-500 dark:text-gray-400">
              With less than a month to go before the European Union enacts new consumer privacy laws for its citizens,
              companies around the world are updating their terms of service agreements to comply.
            </p>
            <p className="text-base leading-relaxed text-gray-500 dark:text-gray-400">
              The European Union’s General Data Protection Regulation (G.D.P.R.) goes into effect on May 25 and is meant
              to ensure a common set of data rights in the European Union. It requires organizations to notify users as
              soon as possible of high-risk data breaches that could personally affect them.
            </p>
          </div>
        </Modal.Body>
        <Modal.Footer>
          
        </Modal.Footer>
      </Modal>
    </>
  );
}

export default SubscriptionModal

export const Status = ({status}: {status: string}) => {
    switch (status) {
      case 'ACTIVE':
        return <span className="text-green-500">Active</span>
      case 'CANCELED':
        return <span className="text-red-500">Canceled</span>
      case 'PAUSED':
        return <span className="text-yellow-500">Paused</span>
      default:
        return <span className="text-gray-500">Unknown</span>
    }
  }
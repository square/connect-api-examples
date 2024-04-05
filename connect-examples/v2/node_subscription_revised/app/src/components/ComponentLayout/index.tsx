// Component layout - a wrapper so we can add a header to the component that 
// tells us what file the component is in

import React, { useContext } from 'react';
import { AppContext } from '../../context/AppContext';

interface ComponentLayoutProps {
    title: string;
    className?: string;
    note?: React.ReactNode;
    children?: React.ReactNode;
}

const ComponentLayout: React.FC<ComponentLayoutProps> = ({title, className, note, children}) => {
    const {showDebug} = useContext(AppContext);
    if (!showDebug) return <>{children}</>
    return <div className='flex flex-col'>
        <div className={`flex justify-end border-b mb-4 ${className ? {className} : ''}`}>
            <h2 className="text-xs text-gray-500">{title}</h2>
        </div>
        {note &&  
          <p className='bg-blue-200 p-2 rounded text-xs'>
            <span className='font-bold'>Note: </span>{note}
        </p>}
        {children} 
    </div>
}

export default ComponentLayout;
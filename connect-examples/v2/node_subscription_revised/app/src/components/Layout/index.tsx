
import React, {useContext} from 'react'
import NavigationButtons from '../NavigationButtons';
import { AppContext } from '../../context/AppContext';


interface LayoutProps {
    title: string;
    subTitle: string;
    children: React.ReactNode;
}

const Layout: React.FC<LayoutProps> = ({title, subTitle, children}) => {
    const {showDebug } = useContext(AppContext);
    return <>
        <div id="container" className='flex'>
            <div id="sectionLabel" className='mr-4'>
                {showDebug ? <p className='text-xs text-gray-500'>{subTitle}</p> : null}
                <span className="text-3xl font-semibold mb-4">{title}</span>
            </div>
            <NavigationButtons/>
        </div>
        <div className='flex justify-center'>
            <div className="p-4 grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3">
                {children}
            </div>
        </div>
    </>
}

export default Layout;
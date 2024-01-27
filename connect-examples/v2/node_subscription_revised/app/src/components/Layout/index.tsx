
import React from 'react'

interface LayoutProps {
    title: string;
    children: React.ReactNode;
}


const Layout: React.FC<LayoutProps> = ({title, children}) => {
    return <>
        <h2 className="text-3xl font-semibold mb-4">{title}</h2>
        <div className="p-4 grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3">
            {children}
        </div>
    </>
}

export default Layout;
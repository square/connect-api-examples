// navbar component
import { useContext } from "react";
import { Link } from "react-router-dom";
import { AppContext, AppDispatchContext } from "../../context/AppContext";

const NavBar = () => {
  const { showDebug } = useContext(AppContext);
  const dispatch = useContext(AppDispatchContext);

  return (
    <header className="bg-black text-white p-4 flex">
      <Link to="/" onClick={() => dispatch({ type: "RESET", payload: {} })}>
        <h1 className="text-2xl font-semibold mr-8">Munch.ie</h1>
      </Link>
      <label className="inline-flex items-center cursor-pointer">
        <input
          type="checkbox"
          value=""
          className="sr-only peer"
          checked={showDebug}
          onChange={() =>
            dispatch({ type: "TOGGLE_DEBUG", payload: !showDebug })
          }
        />
        <div className="relative w-11 h-6 bg-gray-200 peer-focus:outline-none peer-focus:ring-4 peer-focus:ring-blue-300 rounded-full peer peer-checked:after:translate-x-full rtl:peer-checked:after:-translate-x-full peer-checked:after:border-white after:content-[''] after:absolute after:top-[2px] after:start-[2px] after:bg-white after:border-gray-300 after:border after:rounded-full after:h-5 after:w-5 after:transition-all peer-checked:bg-blue-600"></div>
        <span className="ms-3 text-sm font-medium text-white-900">
          Debug Mode
        </span>
      </label>
    </header>
  );
};

export default NavBar;

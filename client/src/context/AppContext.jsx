import { createContext, useEffect, useState } from "react";
import { fetchCategory } from "../Service/CategoryService.js";

export const AppContext = createContext(null);

export const AppContextProvider = (props) => {

    const [categories, setCategories] = useState([]);
    const [auth, setAuth] = useState({
        token: null,
        role: null
    });

    useEffect(() => {

        async function loadData(){
            const response = await fetchCategory();
            setCategories(response.data);
        }

        loadData();

    }, []);

    const setAuthData = (token, role) => {
        setAuth({token, role});
    }

    const contextValue = {
        categories,
        setCategories,
        auth,
        setAuthData
    }

    return <AppContext.Provider value={contextValue}>
        {props.children}
    </AppContext.Provider>

}
// AuthContext.js
import { createContext, useContext, useState, useEffect } from 'react';

const AuthContext = createContext();

export const AuthProvider = ({ children }) => {
  const [token, setToken] = useState(null);

  useEffect(() => {
    const fetchToken = async () => {
      try {
        const response = await fetch('http://localhost:8080/auth/signin', {
          method: 'POST',
          headers: {
            'Content-Type': 'application/json',
          },
          body: JSON.stringify({
            email: 'admin@gmail.com',
            password: 'admin',
          }),
        });

        const data = await response.json();

        if (response.ok) {
          setToken(data.token);
          console.log('Token fetched successfully', data.token);
        } else {
          console.error('Authentication error:', data.error);
        }
      } catch (error) {
        console.error('Error fetching token:', error);
      }
    };

    fetchToken(); // Fetch token on component mount
  }, []);

  return <AuthContext.Provider value={{ token }}>{children}</AuthContext.Provider>;
};

export const useAuth = () => useContext(AuthContext);

import { useReducer } from "react";
import "./App.css";
import { readitReducer } from "./ReaditReducer.tsx";
import { SearchForm } from "./Components/SearchForm.tsx";
import { PostForm } from "./Components/PostForm.tsx";
import { Feed } from "./Components/Feed.tsx";
import { Login, useAuth } from "./Components/Login.tsx";
import type { AuthProps } from "./ReaditTypes.tsx";

const Dashboard = () => {
  const [posts, dispatch] = useReducer(readitReducer, []);

  return (
    <div className="w-full flex items-start gap-10 rounded-xl m-4 p-4">
      <Feed posts={posts} dispatch={dispatch} />
      <div className="w-1/3 sticky top-12">
        <SearchForm dispatch={dispatch} />
        <PostForm dispatch={dispatch} />
      </div>
    </div>
  );
};

const AuthPage = ({ setIsLoggedIn }: AuthProps) => {
  return (
    <div className="mx-auto flex flex-col max-w-lg items-start gap-8 rounded-xl m-4 p-4 outline">
      <Login updater={setIsLoggedIn} />
    </div>
  );
};

const App = () => {
  const [isLoggedIn, setIsLoggedIn] = useAuth();

  return (
    <div>
      {isLoggedIn ? <Dashboard /> : <AuthPage setIsLoggedIn={setIsLoggedIn} />}
    </div>
  );
};

export default App;

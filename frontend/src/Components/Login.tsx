import { useEffect, useState } from "react";
import * as APIHandler from "../ApiHandler";
import "../App.css";

export const useAuth = (): [boolean, (x: boolean) => void] => {
  const [isLoggedIn, setIsLoggedIn] = useState(false);

  useEffect(() => {
    fetch(`${APIHandler.API}/is-logged-in`, {
      credentials: "include",
    })
      .then((x) => x.json())
      .then((res) => {
        setIsLoggedIn(!!res.success);
      });
  }, []);

  return [isLoggedIn, setIsLoggedIn];
};

const handleSubmit = (
  username: string,
  password: string,
  updater: (x: boolean) => void,
) => {
  fetch(`${APIHandler.API}/login`, {
    method: "post",
    headers: {
        "Content-Type": "application/json",
      },
    body: JSON.stringify({ username, password }),
    credentials: "include",
  })
    .then((x) => x.json())
    .then((res) => {
      if (res.success) {
        return updater(true);
      }
      alert("Invalid username or password");
    });
};

type LoginType = {
  label: string;
  placeholder: string;
  value: string;
  onChange: (val: string) => void;
  type?: string;
};

const LoginInput = ({
  label,
  placeholder,
  value,
  onChange,
  type = "text",
}: LoginType) => {
  return (
    <div className="flex flex-col gap-1">
      <label htmlFor={label} className="cursor-pointer">
        {label}
      </label>
      <input
        className="p-2 rounded-sm outline"
        placeholder={placeholder}
        required
        type={type}
        id={label}
        value={value}
        onChange={(e) => onChange(e.target.value)}
      />
    </div>
  );
};

const GithubLoginButton = ({ updater }: { updater: (x: boolean) => void }) => {
  return (
    <button
      className="mt-5 gap-2 w-full bg-gray-900 text-white p-2 rounded-md  hover:bg-black cursor-pointer"
      onClick={() => {
        window.location.href = `${APIHandler.API}/auth`;
        updater(true);
      }}
    >
      Login with GitHub
    </button>
  );
};

export const Login = ({ updater }: { updater: (x: boolean) => void }) => {
  const [username, setUsername] = useState("");
  const [password, setPassword] = useState("");

  return (
    <div className="rounded-lg w-full">
      <h2 className="text-black-800 text-2xl mb-4 text-center">Login</h2>

      <form
        className="flex flex-col gap-4"
        onSubmit={(e) => {
          e.preventDefault();
          handleSubmit(username, password, updater);
        }}
      >
        <LoginInput
          label="Username"
          placeholder="Your username..."
          value={username}
          onChange={setUsername}
        />

        <LoginInput
          label="Password"
          placeholder="Your password..."
          value={password}
          onChange={setPassword}
          type="password"
        />

        <button
          className="bg-blue-400 text-white w-full p-2 rounded-sm cursor-pointer"
          type="submit"
        >
          Login
        </button>
      </form>

      <GithubLoginButton updater={updater} />
    </div>
  );
};

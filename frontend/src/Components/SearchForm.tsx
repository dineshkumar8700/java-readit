import { useEffect, useState } from "react";
import type { Dispatch, SearchedUser } from "../ReaditTypes";
import * as APIHandler from "../ApiHandler";

const fetchUsers = async (targetUser: string): Promise<SearchedUser[]> => {
  const res = await fetch(`${APIHandler.API}/api/users`, {
    method: "POST",
    body: JSON.stringify({ targetUser }),
    credentials: "include",
  });

  const data = await res.json();
  return data.users;
};

const toggleSubscription = async (
  username: string,
  type: "subscribe" | "unsubscribe",
) => {
  await fetch(`${APIHandler.API}/api/${type}`, {
    method: "POST",
    body: JSON.stringify({ user: username }),
    credentials: "include",
  });
};

const useUserSearch = (dispatch: Dispatch) => {
  const [targetUser, setTargetUser] = useState("");
  const [users, setUsers] = useState<SearchedUser[]>([]);
  const [isSearched, setIsSearched] = useState(false);

  const search = async () => {
    if (!targetUser.trim()) return;

    const result = await fetchUsers(targetUser);
    setUsers(result);
    setIsSearched(true);
  };

  const toggle = async (user: SearchedUser) => {
    const type = user.isSubscribed ? "unsubscribe" : "subscribe";

    await toggleSubscription(user.username, type);

    setUsers((prev) =>
      prev.map((u) =>
        u.username === user.username
          ? { ...u, isSubscribed: !u.isSubscribed }
          : u,
      ),
    );

    APIHandler.updateFeed(dispatch);
  };

  return {
    targetUser,
    setTargetUser,
    users,
    isSearched,
    search,
    toggle,
  };
};

type SearchAreaProps = {
  value: string;
  onChange: (x: string) => void;
  onSearch: () => void;
};

const attachDebouncing = (onSearch: () => void, value: string) => {
  useEffect(() => {
    const intervalId = setTimeout(onSearch, 500);

    return () => {
      clearTimeout(intervalId);
    };
  }, [value]);
};

const SearchArea = ({ value, onChange, onSearch }: SearchAreaProps) => {
  attachDebouncing(onSearch, value);

  return (
    <form
      onSubmit={(e) => {
        e.preventDefault();
        onSearch();
      }}
    >
      <div className="flex w-full gap-2">
        <input
          value={value}
          onChange={(e) => {
            onChange(e.target.value);
          }}
          required
          className="w-full rounded-sm p-2 outline"
        />

        <button
          type="submit"
          className="cursor-pointer rounded-sm bg-blue-400 p-2 text-white hover:bg-blue-500"
        >
          Search
        </button>
      </div>
    </form>
  );
};

type UserItemProps = {
  user: SearchedUser;
  onToggle: (user: SearchedUser) => void;
};

const UserItem = ({ user, onToggle }: UserItemProps) => (
  <div className="flex justify-between">
    <div>
      <h4>{user.username}</h4>
      <p>@{user.username}</p>
    </div>

    <button
      onClick={() => onToggle(user)}
      className={`cursor-pointer rounded-sm p-1 self-end outline ${
        user.isSubscribed ? "text-green-500" : "text-blue-500"
      }`}
    >
      {user.isSubscribed ? "Subscribed" : "Subscribe"}
    </button>
  </div>
);

type UserListProps = {
  users: SearchedUser[];
  onToggle: (user: SearchedUser) => void;
};

const UserList = ({ users, onToggle }: UserListProps) => {
  if (!users.length) return null;

  return (
    <div className="mt-4 flex flex-col gap-4">
      {users.map((user) => (
        <UserItem key={user.id} user={user} onToggle={onToggle} />
      ))}
    </div>
  );
};

export const SearchForm = ({ dispatch }: { dispatch: Dispatch }) => {
  const { targetUser, setTargetUser, users, isSearched, search, toggle } =
    useUserSearch(dispatch);

  return (
    <div className="w-full rounded-lg p-4 mb-4 outline">
      <h2 className="mb-4 text-2xl">Search Users</h2>

      <SearchArea
        value={targetUser}
        onChange={setTargetUser}
        onSearch={search}
      />

      {isSearched && targetUser && (
        <p className="mt-2">{users.length} users found</p>
      )}

      <UserList users={users} onToggle={toggle} />
    </div>
  );
};

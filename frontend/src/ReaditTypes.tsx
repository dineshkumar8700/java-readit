export type Post = {
  id: string;
  author: string;
  title: string;
  body: string;
  date: string;
  isLiked: boolean;
  likes: number;
  type: string;
};

export type FeedType = {
  id: string;
  author: string;
  title: string;
  body: string;
  date: string;
  isLiked: boolean;
  likes: number;
  type: string;
};

export type NewPost = {
  title: string;
  body: string;
  date: string;
};

export type Action =
  | { type: "add"; payload: { post: FeedType } }
  | { type: "delete"; payload: { id: string } }
  | { type: "initial-data"; payload: { posts: FeedType[] } };

export type Dispatch = React.Dispatch<Action>;

export type ChangeTitleEvent = React.ChangeEvent<HTMLInputElement>;
export type ChangeBodyEvent = React.ChangeEvent<HTMLTextAreaElement>;
export type AddPostEvent = React.SubmitEvent<HTMLFormElement>;

export type SearchedUser = {
  id: string;
  username: string;
  isSubscribed: boolean;
};
export type AuthProps = {
  setIsLoggedIn: (x: boolean) => void;
};

import type { Action, FeedType } from "./ReaditTypes.tsx";

export const readitReducer = (
  posts: FeedType[],
  action: Action,
): FeedType[] => {
  switch (action.type) {
    case "add":
      return [action.payload.post, ...posts];

    case "delete":
      return posts.filter((post) => post.id !== action.payload.id);

    case "initial-data":
      return action.payload.posts;

    default:
      return posts;
  }
};

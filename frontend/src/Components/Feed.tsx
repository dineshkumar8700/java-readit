import { useEffect, useState } from "react";
import * as APIHandler from "../ApiHandler";
import type { FeedType, Dispatch } from "../ReaditTypes";

const useLike = (initialLiked: boolean, initialLikes: number) => {
  const [isLiked, setIsLiked] = useState(initialLiked);
  const [likes, setLikes] = useState(initialLikes);

  const toggle = async (id: string) => {
    const res = await fetch(`${APIHandler.API}/api/toggle-like`, {
      method: "POST",
      body: JSON.stringify({ postId: id }),
      credentials: "include",
    });

    const { success } = await res.json();

    if (!success) return;

    setIsLiked((prev) => !prev);
    setLikes((l) => (isLiked ? l - 1 : l + 1));
  };

  return { isLiked, likes, toggle };
};

const useFeed = (dispatch: Dispatch) => {
  useEffect(() => {
    APIHandler.fetchInitialPosts(dispatch);
  }, []);
};

const PostHeader = ({ author, type }: { author: string; type: string }) => (
  <div className="flex items-center justify-between">
    <h4 className="text-md text-gray-800 font-semibold">@{author}</h4>
    {type === "own" && (
      <div className="bg-green-200 text-xs text-gray-900 p-1 rounded-sm">
        Your Post
      </div>
    )}
  </div>
);

type PostContentType = { date: string; title: string; body: string };

const PostContent = ({ date, title, body }: PostContentType) => (
  <div>
    <p className="text-sm text-gray-600">{date}</p>
    <h2 className="text-md">{title}</h2>
    <p className="text-sm">{body}</p>
  </div>
);

type LikeButtonType = { isLiked: boolean; likes: number; onClick: () => void };

const LikeButton = ({ isLiked, likes, onClick }: LikeButtonType) => (
  <button onClick={onClick} className="cursor-pointer">
    <span
      className={`outline p-2 rounded-sm ${
        isLiked ? "text-blue-500" : "text-gray-800"
      }`}
    >
      {isLiked ? "Liked" : "Like"} {likes}
    </span>
  </button>
);

const DeleteButton = ({ id, dispatch }: { id: string; dispatch: Dispatch }) => (
  <button
    type="button"
    onClick={() => APIHandler.hanldeDeletePost(id, dispatch)}
    className="cursor-pointer text-white bg-red-500 p-2 rounded-sm"
  >
    Delete
  </button>
);

type PostFooter = {
  post: FeedType;
  dispatch: Dispatch;
  isLiked: boolean;
  likes: number;
  onLike: () => void;
};

const PostFooter = ({ post, dispatch, isLiked, likes, onLike }: PostFooter) => (
  <div className="flex justify-between">
    <LikeButton isLiked={isLiked} likes={likes} onClick={onLike} />
    {post.type === "own" ? (
      <DeleteButton id={post.id} dispatch={dispatch} />
    ) : (
      <p className="text-xs text-center">
        You are only seing posts from user you are subscribed to.
      </p>
    )}
  </div>
);

type PostItem = { post: FeedType; dispatch: Dispatch };

const PostItem = ({ post, dispatch }: PostItem) => {
  const { isLiked, likes, toggle } = useLike(post.isLiked, post.likes);

  return (
    <div className="flex flex-col gap-2 p-4 outline rounded-lg">
      <PostHeader author={post.author} type={post.type} />
      <PostContent date={post.date} title={post.title} body={post.body} />
      <hr className="mb-2 mt-2 text-gray-600" />
      <PostFooter
        post={post}
        dispatch={dispatch}
        isLiked={isLiked}
        likes={likes}
        onLike={() => toggle(post.id)}
      />
    </div>
  );
};

type Feed = { posts: FeedType[]; dispatch: Dispatch };

export const Feed = ({ posts, dispatch }: Feed) => {
  useFeed(dispatch);

  return (
    <div className="outline p-4 rounded-lg w-2/3">
      <h2 className="text-black-800 text-2xl mb-4">Feed</h2>

      {posts.length === 0 ? (
        <p className="text-gray-700">No post found...</p>
      ) : (
        <div className="flex flex-col gap-10">
          {posts.map((post) => (
            <PostItem key={post.id} post={post} dispatch={dispatch} />
          ))}
        </div>
      )}
    </div>
  );
};

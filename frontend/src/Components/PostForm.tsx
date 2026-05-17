import { useState } from "react";
import * as APIHandler from "../ApiHandler";
import { format } from "date-fns";
import type * as ReaditTypes from "../ReaditTypes";

export const getCurrentTime = () => format(new Date(), "MMM d, yyyy • hh:mm a");

const usePostForm = (dispatch: ReaditTypes.Dispatch) => {
  const [title, setTitle] = useState("");
  const [body, setBody] = useState("");

  const reset = () => {
    setTitle("");
    setBody("");
  };

  const submit = (e: ReaditTypes.AddPostEvent) => {
    e.preventDefault();
    if (!title.trim() || !body.trim()) return;

    const post: ReaditTypes.NewPost = { title, body, date: getCurrentTime() };
    APIHandler.handleAddPost(post, dispatch);
    reset();
  };

  return { title, body, setTitle, setBody, submit };
};

export const PostForm = ({ dispatch }: { dispatch: ReaditTypes.Dispatch }) => {
  const { title, body, setTitle, setBody, submit } = usePostForm(dispatch);

  return (
    <div className="outline p-4 rounded-lg w-full">
      <h2 className="text-black-800 text-2xl mb-4">Create Post</h2>

      <form onSubmit={submit} className="flex flex-col gap-2">
        <div className="flex flex-col gap-2 w-full">
          <label htmlFor="title">Title</label>
          <input
            id="title"
            value={title}
            onChange={(e: ReaditTypes.ChangeTitleEvent) =>
              setTitle(e.target.value)
            }
            placeholder="Enter Post Title..."
            required
            className="w-full outline rounded-sm p-2"
          />
        </div>

        <div className="flex flex-col gap-2">
          <label htmlFor="body">Body</label>
          <textarea
            id="body"
            value={body}
            onChange={(e: ReaditTypes.ChangeBodyEvent) =>
              setBody(e.target.value)
            }
            placeholder="Enter Post Description..."
            required
            className="w-full outline rounded-sm p-2 mb-2"
          />
        </div>

        <button
          type="submit"
          className="cursor-pointer text-white bg-blue-400 hover:bg-blue-500 p-2 rounded-sm"
        >
          Post
        </button>
      </form>
    </div>
  );
};

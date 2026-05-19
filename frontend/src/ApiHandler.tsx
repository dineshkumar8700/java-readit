import type { Dispatch, NewPost } from "./ReaditTypes.tsx";

// export const API = "https://readit-dineshkumar8700.onrender.com";
export const API = "http://localhost:9000";

export const fetchInitialPosts = async (dispatch: Dispatch) => {
    const res = await fetch(`${API}/api/posts`, {
        headers: {
          "Content-Type":"application/json",
        },
        credentials: "include",
    });

    const posts = await res.json();
    dispatch({
        type: "initial-data",
        payload: { posts },
    });
};

export const updateFeed = (dispatch: Dispatch) => {
    fetch(`${API}/api/posts`, { credentials: "include" })
        .then((res) => res.json())
        .then((posts) => {
            dispatch({
                type: "initial-data",
                payload: { posts },
            });
        });
};

export const handleAddPost = (newPost: NewPost, dispatch: Dispatch) => {
    fetch(`${API}/api/add-post`, {
        method: "post",
        headers: {
            "Content-Type": "application/json",
        },
        body: JSON.stringify(newPost),
        credentials: "include",
    })
        .then((x) => x.json())
        .then((res) => {
            if (res.success) {
                dispatch({
                    type: "add",
                    payload: { post: res.post },
                });
            }
        });
};

export const hanldeDeletePost = (id: string, dispatch: Dispatch) =>
    fetch(`${API}/api/post`, {
        method: "delete",
        headers: {
            "Content-Type":"application/json",
        },
        body: JSON.stringify({ id }),
        credentials: "include",
    })
        .then((x) => x.json())
        .then((res) => {
            if (res.success) {
                dispatch({ type: "delete", payload: { id } });
            }
        });
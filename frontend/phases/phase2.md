# Backend

Add a backend and have all the interactions update the backend before updating frontend state.

Upon first render, a fetch has to happen to get the initial state of the system and render it. 

# Things to consider

- Cors vs Proxy
- Where will you put the effect?
- If you are using reducers, will the update on the frontend happen before or after the call?
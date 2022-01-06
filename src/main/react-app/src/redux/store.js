import {configureStore} from '@reduxjs/toolkit'
import {photoApi} from './api/photoApi'
import {setupListeners} from '@reduxjs/toolkit/query'
import {userApi} from './api/userApi'
import {hashtagApi} from './api/hashtagApi'
import {authApi} from './api/authApi'

export const store = configureStore({
  reducer: {
    [hashtagApi.reducerPath]: hashtagApi.reducer,
    [photoApi.reducerPath]: photoApi.reducer,
    [userApi.reducerPath]: userApi.reducer,
    [authApi.reducerPath]: authApi.reducer,
  },
  middleware: (getDefaultMiddleware) => getDefaultMiddleware().concat(
    photoApi.middleware, userApi.middleware, hashtagApi.middleware, authApi.middleware
  )
})

setupListeners(store.dispatch)

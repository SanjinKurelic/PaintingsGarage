import {configureStore} from '@reduxjs/toolkit'
import {photoApi} from './api/photoApi'
import {setupListeners} from '@reduxjs/toolkit/query'
import {userApi} from './api/userApi'
import {hashtagApi} from './api/hashtagApi'
import {authApi} from './api/authApi'
import currentUserSlice from './slice/currentUserSlice'
import {auditApi} from './api/auditApi'

export const store = configureStore({
  reducer: {
    [hashtagApi.reducerPath]: hashtagApi.reducer,
    [photoApi.reducerPath]: photoApi.reducer,
    [userApi.reducerPath]: userApi.reducer,
    [authApi.reducerPath]: authApi.reducer,
    [auditApi.reducerPath]: auditApi.reducer,
    currentUser: currentUserSlice
  },
  middleware: (getDefaultMiddleware) => getDefaultMiddleware().concat(
    photoApi.middleware, userApi.middleware, hashtagApi.middleware, authApi.middleware, auditApi.middleware
  )
})

setupListeners(store.dispatch)

import {combineReducers, configureStore} from '@reduxjs/toolkit'
import {photoApi} from './api/photoApi'
import {setupListeners} from '@reduxjs/toolkit/query'
import {userApi} from './api/userApi'
import {hashtagApi} from './api/hashtagApi'
import {authApi} from './api/authApi'
import currentUserSlice from './slice/currentUserSlice'
import {auditApi} from './api/auditApi'
import {persistReducer, persistStore} from 'redux-persist'
import storage from 'redux-persist/lib/storage'
import currentDialogSlice from './slice/currentDialogSlice'

const persistConfig = {
  key: 'root',
  storage,
  whitelist: ['currentUser']
}

const reducers = combineReducers({
  [hashtagApi.reducerPath]: hashtagApi.reducer,
  [photoApi.reducerPath]: photoApi.reducer,
  [userApi.reducerPath]: userApi.reducer,
  [authApi.reducerPath]: authApi.reducer,
  [auditApi.reducerPath]: auditApi.reducer,
  currentUser: currentUserSlice,
  currentDialog: currentDialogSlice
})

export const store = configureStore({
  reducer: persistReducer(persistConfig, reducers),
  middleware: (getDefaultMiddleware) => getDefaultMiddleware().concat(
    photoApi.middleware, userApi.middleware, hashtagApi.middleware, authApi.middleware, auditApi.middleware,
    currentUserSlice.middleware, currentDialogSlice,middleware
  )
})

export const persistor = persistStore(store)

setupListeners(store.dispatch)

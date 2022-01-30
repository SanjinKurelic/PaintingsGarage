import {combineReducers, configureStore} from '@reduxjs/toolkit'
import {setupListeners} from '@reduxjs/toolkit/query'
import currentUserSlice from './slice/currentUserSlice'
import {persistReducer, persistStore} from 'redux-persist'
import storage from 'redux-persist/lib/storage'
import currentDialogSlice from './slice/currentDialogSlice'
import {baseApi} from './api/baseApi'

const persistConfig = {
  key: 'root',
  storage,
  whitelist: ['currentUser']
}

const reducers = combineReducers({
  [baseApi.reducerPath]: baseApi.reducer,
  currentUser: currentUserSlice,
  currentDialog: currentDialogSlice
})

export const store = configureStore({
  reducer: persistReducer(persistConfig, reducers),
  middleware: (getDefaultMiddleware) => getDefaultMiddleware().concat(baseApi.middleware)
})

export const persistor = persistStore(store)

setupListeners(store.dispatch)

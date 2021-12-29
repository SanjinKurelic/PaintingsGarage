import {configureStore} from '@reduxjs/toolkit'
import {photoApi} from './api/photoApi'
import {setupListeners} from '@reduxjs/toolkit/query'

export const store = configureStore({
  reducer: {
    [photoApi.reducerPath]: photoApi.reducer
  },
  middleware: (getDefaultMiddleware) => getDefaultMiddleware().concat(photoApi.middleware)
})

setupListeners(store.dispatch)
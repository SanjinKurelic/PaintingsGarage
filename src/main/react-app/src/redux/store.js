import {configureStore} from '@reduxjs/toolkit'
import {imageSlice} from './imageSlice'

export default configureStore({
  reducer: {
    imageSlice: imageSlice.reducer
  }
})
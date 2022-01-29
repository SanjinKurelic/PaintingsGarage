import {createSlice} from '@reduxjs/toolkit'
import {readFromLocalStorage, storeToLocalStorage} from '../../util/LocalStorageUtil'

const currentUserSlice = createSlice({
  name: 'currentUser',
  initialState: {user: readFromLocalStorage('USER-ID')},
  reducers: {
    setCurrentUser: (state, {payload}) => {
      state.user = payload
      storeToLocalStorage('USER-ID', payload)
    }
  },
})

export const {setCurrentUser} = currentUserSlice.actions

export default currentUserSlice.reducer

export const selectCurrentUser = (state) => state.currentUser.user

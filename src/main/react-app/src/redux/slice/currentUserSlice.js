import {createSlice} from '@reduxjs/toolkit'

const currentUserSlice = createSlice({
  name: 'currentUser',
  initialState: {user: localStorage.getItem('USER-ID')},
  reducers: {
    setCurrentUser: (state, {payload}) => {
      state.user = payload
      localStorage.setItem('USER-ID', payload)
    }
  },
})

export const {setCurrentUser} = currentUserSlice.actions

export default currentUserSlice.reducer

export const selectCurrentUser = (state) => state.currentUser.user

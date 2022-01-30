import {createSlice} from '@reduxjs/toolkit'

const initialState = { user: null }

const currentUserSlice = createSlice({
  name: 'currentUser',
  initialState,
  reducers: {
    setCurrentUser: (state, {payload}) => payload,
    deleteCurrentUser: () => null
  },
})

export const {setCurrentUser, deleteCurrentUser} = currentUserSlice.actions

export default currentUserSlice.reducer

export const selectCurrentUser = (state) => state.currentUser

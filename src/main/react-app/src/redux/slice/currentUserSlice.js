import {createSlice} from '@reduxjs/toolkit'
import {checkToken} from '../../util/TokenValidator'

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

export const selectCurrentUser = (state) => {
  if (!checkToken(state.currentUser)) {
    deleteCurrentUser()
  }

  return state.currentUser
}

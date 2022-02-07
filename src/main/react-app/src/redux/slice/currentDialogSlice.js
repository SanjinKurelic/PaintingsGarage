import {createSlice} from '@reduxjs/toolkit'

const initialState = {type: null, data: null}

const currentDialogSlice = createSlice({
  name: 'currentDialog',
  initialState,
  reducers: {
    showDialog: (state, {payload}) => payload,
    hideDialog: () => null
  },
})

export const {showDialog, hideDialog} = currentDialogSlice.actions

export default currentDialogSlice.reducer

export const selectCurrentDialog = (state) => state.currentDialog

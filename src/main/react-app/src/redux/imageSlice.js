import {createSlice} from "@reduxjs/toolkit";

export const imageSlice = createSlice({
  name: 'imageSlice',
  initialState: {
    images: []
  },
  reducers: {
    fillLastImages: (state, action) => {
      state.images = action.payload;
    }
  }
});

export const {fillLastImages} = imageSlice.actions;

export default imageSlice.reducer;
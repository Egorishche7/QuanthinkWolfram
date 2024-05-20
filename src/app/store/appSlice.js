import { createSlice } from '@reduxjs/toolkit';

const initialState = {
  language: 'en',
  selectedLibrary: 'JAVA',
  // Add other initial state properties here
};

const appSlice = createSlice({
  name: 'app',
  initialState,
  reducers: {
    setLanguage: (state, action) => {
      state.language = action.payload;
    },
    changeLibrary: (state, action) => {
      state.selectedLibrary = action.payload;
    },
    // Add other reducers here
  },
});

export const { setLanguage, changeLibrary } = appSlice.actions;
export default appSlice.reducer;
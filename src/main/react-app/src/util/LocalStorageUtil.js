const readFromLocalStorage = (key) => {
  const item = localStorage.getItem(key)
  try {
    JSON.parse(item)
  } catch (e) {
    return null
  }
}

const storeToLocalStorage = (key, item) => {
  try {
    localStorage.setItem(key, JSON.stringify(item))
  } catch (e) {
    console.log('Item (below) can not be saved to localStorage with key ' + key)
    console.log(item)
  }
}

export {readFromLocalStorage, storeToLocalStorage}

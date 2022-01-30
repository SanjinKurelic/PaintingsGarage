import React from 'react'
import ReactDOM from 'react-dom'
import 'bootstrap/dist/css/bootstrap.min.css'
import App from './App'
import {persistor, store} from './redux/store'
import {Provider} from 'react-redux'
import {PersistGate} from 'redux-persist/integration/react'
import {CartProvider} from 'react-use-cart'

ReactDOM.render(
  <React.StrictMode>
    <Provider store={store}>
      <PersistGate persistor={persistor}>
        <CartProvider>
          <App/>
        </CartProvider>
      </PersistGate>
    </Provider>
  </React.StrictMode>,
  document.getElementById('root')
)

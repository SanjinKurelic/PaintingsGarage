import {Container} from 'react-bootstrap'
import Header from './components/header/Header'
import {useState} from 'react'
import Footer from './components/footer/Footer'
import {BrowserRouter, Route, Switch} from 'react-router-dom'
import Login from './components/login/Login'
import User from './components/user/User'
import ProtectedRoute from './util/ProtectedRoute'
import Admin from './components/admin/Admin'
import {useSelector} from 'react-redux'
import {selectCurrentDialog} from './redux/slice/currentDialogSlice'
import {getDialog} from './components/dialog/AllDialogs'
import HomePage from './components/home/HomePage'
import Cart from './components/cart/Cart'
import NotFound from './components/error/NotFound'

function App() {
  const [searchImageResults, setSearchImageResults] = useState(null)
  const [searchFired, setSearchFired] = useState(false)
  const [clearSearch, setClearSearch] = useState(false)
  const currentDialog = useSelector(selectCurrentDialog)

  return (
    <BrowserRouter>
      <Header setSearchImageResults={setSearchImageResults} setSearchFired={setSearchFired} clearSearch={clearSearch}
              setClearSearch={setClearSearch}/>
      <Container style={{marginBottom: '58px'}}>
        <>
          {currentDialog && getDialog(currentDialog.type, currentDialog.data)}
          <Switch>
            <Route path="/" exact
                   render={() => <HomePage searchImageResults={searchImageResults} searchFired={searchFired}
                                           setSearchFired={setSearchFired} setClearSearch={setClearSearch}/>}/>
            <Route path="/cart" component={Cart}/>
            <Route path="/login" component={Login}/>
            <Route path="/notFound" component={NotFound}/>
            <ProtectedRoute path="/user" componentForRole={{user: User, admin: Admin}}/>
          </Switch>
        </>
      </Container>
      <Footer/>
    </BrowserRouter>
  )
}

export default App

import logo from '../../assets/logo.png'
import {Button, Container, Image, Nav} from 'react-bootstrap'
import Search from '../search/Search'
import './header.scss'
import {IoCartOutline, VscAccount} from 'react-icons/all'
import {useHistory} from 'react-router-dom'
import {useCart} from 'react-use-cart'
import PropTypes from 'prop-types'

const Header = ({setSearchImageResults, setSearchFired, clearSearch, setClearSearch}) => {
  const {isEmpty, totalUniqueItems} = useCart()
  const history = useHistory()

  return (
    <Nav className="bg-dark">
      <Container className="d-flex justify-content-between p-2">
        <Image alt="Sell my picture" className="m-2 cursor-pointer" src={logo} height={40}
               onClick={() => history.push('/')}/>
        <Search setSearchImageResults={setSearchImageResults} setSearchFired={setSearchFired}
                clearSearch={clearSearch} setClearSearch={setClearSearch}/>
        <div>
          <Button variant="link" size="lg" className="header-link" onClick={() => history.push('/user')}><VscAccount
            size={28}/></Button>
          <Button variant="link" size="lg" className="header-link" onClick={() => history.push('/cart')}><IoCartOutline
            size={28}/>
            <div
              className="d-inline-block align-top text-decoration-underline text-small">{!isEmpty && totalUniqueItems}</div>
          </Button>
        </div>
      </Container>
    </Nav>
  )
}

Header.propTypes = {
  setSearchImageResults: PropTypes.func.isRequired,
  setSearchFired: PropTypes.func.isRequired,
  setClearSearch: PropTypes.func.isRequired,
  clearSearch: PropTypes.bool
}

Header.defaultProps = {
  clearSearch: false
}

export default Header

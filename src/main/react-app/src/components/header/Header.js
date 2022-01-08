import logo from '../../assets/logo.png'
import {Button, Container, Image, Nav} from 'react-bootstrap'
import Search from '../search/Search'
import './header.scss'
import {IoCartOutline, VscAccount} from 'react-icons/all'
import {useHistory} from 'react-router-dom'

const Header = ({setSearchImageResults, setSearchFired}) => {
  const history = useHistory()

  return (
    <Nav className="bg-dark">
      <Container className="d-flex justify-content-between p-2">
        <Image alt="Sell my picture" className="my-2 header-logo" src={logo} height={40} onClick={() => history.push('/')}/>
        <Search setSearchImageResults={setSearchImageResults} setSearchFired={setSearchFired}/>
        <div>
          <Button variant="link" size="lg" className="header-link" onClick={() => history.push('/user')}><VscAccount
            size={28}/></Button>
          <Button variant="link" size="lg" className="header-link" onClick={() => history.push('/cart')}><IoCartOutline
            size={28}/></Button>
        </div>
      </Container>
    </Nav>
  )
}

export default Header
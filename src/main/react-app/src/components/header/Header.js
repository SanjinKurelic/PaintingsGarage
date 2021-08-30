import logo from '../../assets/logo.png'
import {Button, Form, FormControl, Image, InputGroup, Nav} from 'react-bootstrap'
import {BsSearch} from 'react-icons/bs'
import {FaShoppingCart, FaUser} from 'react-icons/fa'

const Header = () => {
  return (
    <Nav className="d-flex justify-content-between p-2">
      <Image src={logo} height={40}/>
      <Form className="mx-3 flex-grow-1" style={{maxWidth: '600px'}}>
        <InputGroup>
          <FormControl/>
          <InputGroup.Text><BsSearch/></InputGroup.Text>
        </InputGroup>
      </Form>
      <div>
        <Button variant="link" size="lg"><FaUser size={28} color="black"/></Button>
        <Button variant="link" size="lg"><FaShoppingCart size={28} color="black"/></Button>
      </div>
    </Nav>
  )
}

export default Header
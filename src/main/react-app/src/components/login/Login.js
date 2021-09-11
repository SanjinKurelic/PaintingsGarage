import {Button, Col, Container, Form, Image, Row} from 'react-bootstrap'
import {FaGithubAlt, GrGoogle} from 'react-icons/all'
import './login.scss'
import {useState} from 'react'
import {hostname} from '../../redux/api/baseApi'

const Login = () => {
  const [validated, setValidate] = useState(false)
  const [action, setAction] = useState('/login')

  const handleSubmit = (event) => {
    const form = event.currentTarget

    if (form.checkValidity() === false) {
      event.preventDefault()
      event.stopPropagation()
    }

    setValidate(true)
  }

  const handleSubmitUrl = (event) => {
    const radioButton = event.currentTarget
    setAction('/' + radioButton.value)
  }

  return (
    <Container fluid className="mt-5 mx-sm-0">
      <Row className="d-flex justify-content-center align-items-center h-100">
        <Col className="col-5 col-lg-6 col-xl-5">
          <Image src={hostname + '/login-screen-logo.png'} className="img-fluid" alt=""/>
        </Col>
        <Col className="col-8 col-lg-6 col-xl-4 offset-xl-1 mt-5">
          <Form noValidate validated={validated} onSubmit={handleSubmit} action={hostname + action} method="POST">
            <div className="d-flex flex-row align-items-center justify-content-center justify-content-lg-start">
              <p className="lead fw-normal mb-0 me-5">Sign in with</p>
              <Button className="mx-2 p-2 rounded-circle login-social-media-button">
                <GrGoogle size={22} className="text-center"/>
              </Button>
              <Button className="mx-2 p-2 rounded-circle login-social-media-button">
                <FaGithubAlt size={22} className="text-center"/>
              </Button>
            </div>
            <div className="login-divider d-flex align-items-center my-4">
              <p className="text-center fw-bold mx-3 mb-0">Or</p>
            </div>
            <Form.Group as={Row} className="mb-4">
              <Form.Label column className="col-3"><b>Type:</b></Form.Label>
              <Col>
                <Form.Check inline type="radio" label="Existing user" name="userType" value="login"
                            defaultChecked onChange={handleSubmitUrl}/>
                <Form.Check inline type="radio" label="New user" name="userType" value="register"
                            onChange={handleSubmitUrl}/>
              </Col>
            </Form.Group>
            <Form.Group as={Row} className="mb-4" hidden={action === '/login'}>
              <Form.Label column className="col-3"><b>Name:</b></Form.Label>
              <Col>
                <Form.Control required={action === '/register'} type="text" name="name"
                              placeholder="Enter your name"/>
                <Form.Control.Feedback type="invalid">
                  Please enter valid name.
                </Form.Control.Feedback>
              </Col>
            </Form.Group>
            <Form.Group as={Row} className="mb-4">
              <Form.Label column className="col-3"><b>Email:</b></Form.Label>
              <Col>
                <Form.Control required type="email" name="username" placeholder="Enter email"/>
                <Form.Control.Feedback type="invalid">
                  Please enter valid email.
                </Form.Control.Feedback>
              </Col>
            </Form.Group>
            <Form.Group as={Row} className="mb-4">
              <Form.Label column className="col-3"><b>Password:</b></Form.Label>
              <Col>
                <Form.Control required minLength={8} type="password" name="password" placeholder="Password"/>
                <Form.Control.Feedback type="invalid">
                  Please enter valid password, at least 8 characters long.
                </Form.Control.Feedback>
              </Col>
            </Form.Group>
            <div className="text-center text-lg-end mt-4 pt-2">
              <Button className="login-submit-button" variant="primary" type="submit">Login</Button>
            </div>
          </Form>
        </Col>
      </Row>
    </Container>
  )
}

export default Login
import {Button, Col, Container, Form, Image, Row} from 'react-bootstrap'
import {FaGithubAlt, GrGoogle} from 'react-icons/all'
import './login.scss'
import {useCallback, useEffect, useState} from 'react'
import {hostname} from '../../redux/api/baseApi'
import {useLoginUserMutation, useRegisterUserMutation} from '../../redux/api/authApi'
import {useHistory} from 'react-router-dom'
import {storeCurrentUser} from '../../redux/auth'

const Login = () => {
  // Action type constants and state
  const LOGIN = 'LOGIN'
  const REGISTER = 'REGISTER'
  const [action, setAction] = useState(LOGIN)

  // Validation
  const [validated, setValidate] = useState(false)

  // Form values
  const [inputs, setInputs] = useState({})

  const changeInput = (e) => {
    setInputs(values => ({...values, [e.target.name]: e.target.value}))
  }

  // API calls
  const [login, loginResult] = useLoginUserMutation()
  const [register, registerResult] = useRegisterUserMutation()

  const submit = (event) => {
    const form = event.currentTarget

    if (form.checkValidity() !== false) {
      let data = {email: inputs.email, password: inputs.password}
      switch (action) {
        case LOGIN:
          login(data)
          break
        case REGISTER:
          register(...{name: inputs.name}, ...data)
          break
        default:
          return
      }
    }

    setValidate(true)
    event.preventDefault()
    event.stopPropagation()
  }

  // Change UI elements
  const changeSubmitUrl = (event) => {
    setAction(event.currentTarget.value)
  }

  // If user successfully register/login
  const history = useHistory()
  const storeAndRedirect = useCallback((result) => {
    if (result.isSuccess && result.data && result.data.token) {
      storeCurrentUser(result.data)
      // Clean inputs
      setInputs({})
      // redirect
      history.push('/')
    }
  }, [setInputs, history])
  useEffect(() => {
    storeAndRedirect(loginResult)
  }, [loginResult, storeAndRedirect])
  useEffect(() => {
    storeAndRedirect(registerResult)
  }, [registerResult, storeAndRedirect])

  return (
    <Container fluid className="mt-5 mx-sm-0">
      <Row className="d-flex justify-content-center align-items-center h-100">
        <Col className="col-5 col-lg-6 col-xl-5">
          <Image src={hostname + '/login-screen-logo.png'} className="img-fluid" alt=""/>
        </Col>
        <Col className="col-8 col-lg-6 col-xl-4 offset-xl-1 mt-5">
          {(loginResult.isError || registerResult.isError) && <div className="alert alert-danger">
            {loginResult.isError && <span>Wrong email password combination</span>}
            {registerResult.isError && <span>Name or email already exists</span>}
          </div>}
          <Form noValidate validated={validated} onSubmit={submit} className="login">
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
                <Form.Check inline type="radio" label="Existing user" name="userType" value={LOGIN}
                            defaultChecked onChange={changeSubmitUrl}/>
                <Form.Check inline type="radio" label="New user" name="userType" value={REGISTER}
                            onChange={changeSubmitUrl}/>
              </Col>
            </Form.Group>
            <Form.Group as={Row} className="mb-4" hidden={action === LOGIN}>
              <Form.Label column className="col-3"><b>Name:</b></Form.Label>
              <Col>
                <Form.Control required={action === REGISTER} type="text" name="name" className="login-input-text"
                              placeholder="Enter your name" onChange={changeInput}/>
                <Form.Control.Feedback type="invalid">
                  Please enter valid name.
                </Form.Control.Feedback>
              </Col>
            </Form.Group>
            <Form.Group as={Row} className="mb-4">
              <Form.Label column className="col-3"><b>Email:</b></Form.Label>
              <Col>
                <Form.Control required type="email" name="email" placeholder="Enter email" onChange={changeInput}
                              className="login-input-text"/>
                <Form.Control.Feedback type="invalid">
                  Please enter valid email.
                </Form.Control.Feedback>
              </Col>
            </Form.Group>
            <Form.Group as={Row} className="mb-4">
              <Form.Label column className="col-3"><b>Password:</b></Form.Label>
              <Col>
                <Form.Control required minLength={8} type="password" name="password" placeholder="Password"
                              className="login-input-text" onChange={changeInput}/>
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

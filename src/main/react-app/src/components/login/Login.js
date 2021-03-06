import {Button, Col, Container, Form, Image, Row} from 'react-bootstrap'
import {FaGithubAlt, GrGoogle} from 'react-icons/all'
import './login.scss'
import {useCallback, useEffect, useState} from 'react'
import {hostname, useLoginUserMutation, useRegisterUserMutation} from '../../redux/api/baseApi'
import {useHistory} from 'react-router-dom'
import {useDispatch} from 'react-redux'
import {setCurrentUser} from '../../redux/slice/currentUserSlice'
import Plan from '../plan/Plan'

const Login = () => {
  // Action type constants and state
  const LOGIN = 'LOGIN'
  const REGISTER = 'REGISTER'
  const [action, setAction] = useState(LOGIN)

  // Selected plan
  const [selectedPlan, setSelectedPlan] = useState(0)

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
          register(Object.assign(data, {name: inputs.name, plan: selectedPlan}))
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
  const dispatch = useDispatch()
  const history = useHistory()
  const storeAndRedirect = useCallback((result) => {
    if (result.isSuccess && result.data && result.data.token) {
      dispatch(setCurrentUser({user: result.data}))
      // Clean inputs
      setInputs({})
      // redirect
      history.push('/')
    }
  }, [setInputs, history, dispatch])
  useEffect(() => {
    storeAndRedirect(loginResult)
  }, [loginResult, storeAndRedirect])
  useEffect(() => {
    storeAndRedirect(registerResult)
  }, [registerResult, storeAndRedirect])

  return (
    <Container fluid className="mt-5 mx-sm-0">
      <Row className="d-flex justify-content-center align-items-start h-100">
        <Col className="col-5 col-lg-6 col-xl-5">
          <Image src={hostname + '/login-screen-logo.png'} className="img-fluid" alt=""/>
        </Col>
        <Col className="col-8 col-lg-6 col-xl-4 offset-xl-1 mt-5">
          {(loginResult.isError || registerResult.isError) && <div className="alert alert-danger">
            {loginResult.isError && <span>Invalid email or password</span>}
            {registerResult.isError && <span>Name or email already exists</span>}
          </div>}
          <Form noValidate validated={validated} onSubmit={submit} className="login">
            <div className="d-flex flex-row align-items-center justify-content-center justify-content-lg-start">
              <p className="lead fw-normal mb-0 me-5">Sign in with</p>
              <Button className="mx-2 p-2 rounded-circle button-dark login-social-media-button">
                <GrGoogle size={22} className="text-center"/>
              </Button>
              <Button className="mx-2 p-2 rounded-circle button-dark login-social-media-button">
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
                            className="input-light" defaultChecked onChange={changeSubmitUrl}/>
                <Form.Check inline type="radio" label="New user" name="userType" value={REGISTER}
                            className="input-light" onChange={changeSubmitUrl}/>
              </Col>
            </Form.Group>
            <Form.Group as={Row} className="mb-4" hidden={action === LOGIN}>
              <Form.Label column className="col-3"><b>Name:</b></Form.Label>
              <Col>
                <Form.Control required={action === REGISTER} type="text" name="name" className="input-light"
                              placeholder="Enter your name" onChange={changeInput}/>
                <Form.Control.Feedback type="invalid">
                  Please enter valid name.
                </Form.Control.Feedback>
              </Col>
            </Form.Group>
            <Form.Group as={Row} className="mb-4">
              <Form.Label column className="col-3"><b>Email:</b></Form.Label>
              <Col>
                <Form.Control required type="email" name="email" placeholder="Enter your email" onChange={changeInput}
                              className="input-light"/>
                <Form.Control.Feedback type="invalid">
                  Please enter valid email.
                </Form.Control.Feedback>
              </Col>
            </Form.Group>
            <Form.Group as={Row} className="mb-4">
              <Form.Label column className="col-3"><b>Password:</b></Form.Label>
              <Col>
                <Form.Control required minLength={8} type="password" name="password" placeholder="Enter your password"
                              className="input-light" onChange={changeInput}/>
                <Form.Control.Feedback type="invalid">
                  Please enter valid password, at least 8 characters long.
                </Form.Control.Feedback>
              </Col>
            </Form.Group>
            <Form.Group as={Row} className="mb-4" hidden={action === LOGIN}>
              <Form.Label column className="col-3"><b>Plan:</b></Form.Label>
              <Col>
                <Form.Control hidden={true} required={action === REGISTER} name="plan" value={selectedPlan} readOnly/>
                <Plan visibleDetails={true} selectedPlan={selectedPlan} setSelectedPlan={setSelectedPlan}/>
              </Col>
            </Form.Group>
            <div className="text-center text-lg-end mt-4 pt-2">
              <Button className="button-dark" variant="primary"
                      type="submit">{action === LOGIN ? 'Login' : 'Register'}</Button>
            </div>
          </Form>
        </Col>
      </Row>
    </Container>
  )
}

export default Login

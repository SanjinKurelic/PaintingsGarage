import {Button, Card, Col, Container, Row} from 'react-bootstrap'
import {hostname} from '../../redux/api/baseApi'
import {setCurrentUser} from '../../redux/slice/currentUserSlice'
import {useDispatch} from 'react-redux'
import PropTypes from 'prop-types'
import Plan from '../plan/Plan'
import {useEffect, useState} from 'react'

const UserDetails = ({userDetails, showLogoutButton, className}) => {
  const dispatch = useDispatch()
  const resolvePlan = (plan) => {
    return plan === 'ARTIST' ? 1 : 0
  }
  const [selectedPlan, setSelectedPlan] = useState(resolvePlan(userDetails.plan))
  // If plan change, set new one
  useEffect(() => {
    setSelectedPlan(resolvePlan(userDetails.plan))
  }, [userDetails.plan])

  return (
    <Row className={className}>
      <Col className="text-center col-2">
        <img className="mt-3" alt="" src={hostname + '/' + userDetails.avatar} height="100"/>
      </Col>
      <Col className={showLogoutButton ? "col-6" : "col-7"}>
        <Card className="user-details text-start">
          <Card.Body>
            <Card.Title>User details</Card.Title>
            <Card.Text>
              <Container>
                <Row>
                  <Col className="fw-bold">Name:</Col>
                  <Col>{userDetails.name}</Col>
                </Row>
                <Row>
                  <Col className="fw-bold">Email:</Col>
                  <Col>{userDetails.email}</Col>
                </Row>
                <Row>
                  <Col className="fw-bold">Date of registration:</Col>
                  <Col>{userDetails.registered}</Col>
                </Row>
              </Container>
            </Card.Text>
          </Card.Body>
        </Card>
      </Col>
      <Col className={showLogoutButton ? "col-2" : "col-3"}>
        <Card>
          <Card.Body>
            <Card.Title>Change plan</Card.Title>
            <Card.Text>
              <Plan setSelectedPlan={setSelectedPlan} selectedPlan={selectedPlan}/>
            </Card.Text>
          </Card.Body>
        </Card>
      </Col>
      {showLogoutButton && <Col className="col-2"><Button className="user-action-button d-block m-2" variant="primary"
                                                          onClick={() => dispatch(setCurrentUser(null))}>Logout</Button></Col>}
    </Row>
  )
}

UserDetails.propTypes = {
  className: PropTypes.string,
  userDetails: PropTypes.object,
  showLogoutButton: PropTypes.bool
}

UserDetails.defaultProps = {
  className: '',
  showLogoutButton: true
}

export default UserDetails

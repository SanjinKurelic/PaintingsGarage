import {Button, ListGroup, ModalBody, ModalDialog, ModalFooter, ModalTitle} from 'react-bootstrap'
import buyer from '../../assets/buyer.png'
import painter from '../../assets/painter.png'
import PropTypes from 'prop-types'
import './plan.scss'
import {useChangePlanMutation} from '../../redux/api/userApi'
import ModalHeader from 'react-bootstrap/ModalHeader'
import {useState} from 'react'

const Plan = ({visibleDetails, selectedPlan, setSelectedPlan}) => {
  const [changePlan] = useChangePlanMutation()
  const [focusedPlan, setFocusedPlan] = useState(selectedPlan)
  const [showDialog, setShowDialog] = useState(false)

  const focusPlanAndShowDialog = (value) => {
    if (visibleDetails === true) {
      setSelectedPlan(value)
    } else {
      setFocusedPlan(value)
      setShowDialog(true)
    }
  }

  return (
    <>
      <div hidden={showDialog === false || visibleDetails === true}
           className="position-fixed top-0 bottom-0 w-100 plan-confirmation">
        <ModalDialog centered={true}>
          <ModalHeader>
            <ModalTitle>Confirmation</ModalTitle>
          </ModalHeader>
          <ModalBody>Are you sure you want to change current plan?</ModalBody>
          <ModalFooter>
            <Button className="plan-confirmation-button" onClick={() => setShowDialog(false)}>No</Button>
            <Button className="plan-confirmation-button" onClick={() => {
              setShowDialog(false)
              setSelectedPlan(focusedPlan)
              changePlan(focusedPlan === 0 ? 'BUYER' : 'ARTIST')
            }}>Yes</Button>
          </ModalFooter>
        </ModalDialog>
      </div>
      <ListGroup horizontal className="plan">
        <ListGroup.Item className="plan-item text-center" data-selected={selectedPlan === 0}
                        onClick={() => focusPlanAndShowDialog(0)}>
          <img height="50px" alt="Buyer" src={buyer}/>
          {visibleDetails && <ListGroup variant="flush" className="plan-item-description mt-2">
            <ListGroup.Item className="plan-item-description-first">Can buy images</ListGroup.Item>
            <ListGroup.Item className="border-0">&nbsp;</ListGroup.Item>
            <ListGroup.Item>Free</ListGroup.Item>
          </ListGroup>}
        </ListGroup.Item>
        <ListGroup.Item className="plan-item text-center" data-selected={selectedPlan === 1}
                        onClick={() => focusPlanAndShowDialog(1)}>
          <img height="50px" alt="Painter" src={painter}/>
          {visibleDetails && <ListGroup variant="flush" className="plan-item-description mt-2">
            <ListGroup.Item className="plan-item-description-first">Can buy images</ListGroup.Item>
            <ListGroup.Item className="plan-item-no-padding">Can sell own images</ListGroup.Item>
            <ListGroup.Item>3€ per month</ListGroup.Item>
          </ListGroup>}
        </ListGroup.Item>
      </ListGroup>
    </>
  )
}

Plan.propTypes = {
  visibleDetails: PropTypes.bool,
  selectedPlan: PropTypes.number.isRequired,
  setSelectedPlan: PropTypes.func.isRequired
}

Plan.defaultPropTypes = {
  visibleDetails: false
}

export default Plan

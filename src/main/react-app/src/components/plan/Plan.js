import {ListGroup} from 'react-bootstrap'
import buyer from '../../assets/buyer.png'
import painter from '../../assets/painter.png'
import PropTypes from 'prop-types'
import './plan.scss'
import {useState} from 'react'
import {useDispatch} from 'react-redux'
import {showDialog} from '../../redux/slice/currentDialogSlice'
import {DialogType} from '../dialog/AllDialogs'
import {useUpdateUserMutation} from '../../redux/api/baseApi'

const Plan = ({visibleDetails, userId, selectedPlan, setSelectedPlan}) => {
  const dispatch = useDispatch()
  const [changePlan] = useUpdateUserMutation()
  const [focusedPlan, setFocusedPlan] = useState(selectedPlan)

  const focusPlanAndShowDialog = (value) => {
    if (visibleDetails === true) {
      setSelectedPlan(value)
    } else {
      setFocusedPlan(value)
      dispatch(showDialog({type: DialogType.CHANGE_PLAN, data: confirmChangePlan}))
    }
  }

  const confirmChangePlan = () => {
    setSelectedPlan(focusedPlan)
    changePlan({userId, plan: focusedPlan === 0 ? 'BUYER' : 'ARTIST'})
  }

  return (
    <>
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
  userId: PropTypes.number,
  selectedPlan: PropTypes.number.isRequired,
  setSelectedPlan: PropTypes.func.isRequired
}

Plan.defaultPropTypes = {
  visibleDetails: false
}

export default Plan

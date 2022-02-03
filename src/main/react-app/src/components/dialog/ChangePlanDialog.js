import {Button, ModalBody, ModalDialog, ModalFooter, ModalTitle} from 'react-bootstrap'
import ModalHeader from 'react-bootstrap/ModalHeader'
import PropTypes from 'prop-types'
import {useDispatch} from 'react-redux'
import {hideDialog} from '../../redux/slice/currentDialogSlice'

const ChangePlanDialog = ({callback}) => {
  const dispatch = useDispatch()

  const no = () => {
    dispatch(hideDialog())
  }

  const yes = () => {
    dispatch(hideDialog())
    callback()
  }

  return (
    <div className="position-fixed top-0 bottom-0 w-100 dialog-box">
      <ModalDialog centered={true}>
        <ModalHeader>
          <ModalTitle>Confirmation</ModalTitle>
        </ModalHeader>
        <ModalBody>Are you sure you want to change current plan?</ModalBody>
        <ModalFooter>
          <Button className="button-dark" onClick={no}>No</Button>
          <Button className="button-dark" onClick={yes}>Yes</Button>
        </ModalFooter>
      </ModalDialog>
    </div>
  )
}

ChangePlanDialog.propTypes = {
  callback: PropTypes.func.isRequired
}

export default ChangePlanDialog

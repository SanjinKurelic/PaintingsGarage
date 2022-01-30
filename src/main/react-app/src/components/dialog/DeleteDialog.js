import {Button, ModalBody, ModalDialog, ModalFooter, ModalTitle} from 'react-bootstrap'
import ModalHeader from 'react-bootstrap/ModalHeader'
import PropTypes from 'prop-types'
import {useDispatch} from 'react-redux'
import {hideDialog} from '../../redux/slice/currentDialogSlice'
import {useDeletePhotoMutation} from '../../redux/api/baseApi'

const DeleteDialog = ({image}) => {
  const dispatch = useDispatch()
  const deleteImage = useDeletePhotoMutation()

  const no = () => {
    dispatch(hideDialog())
  }

  const yes = () => {
    deleteImage(image.id)
    dispatch(hideDialog())
  }

  return (
    <div className="position-fixed top-0 bottom-0 w-100 dialog-box">
      <ModalDialog centered={true}>
        <ModalHeader>
          <ModalTitle>Confirmation</ModalTitle>
        </ModalHeader>
        <ModalBody>Are you sure you want to delete "{image.title}" image?</ModalBody>
        <ModalFooter>
          <Button className="dialog-box-button" onClick={no}>No</Button>
          <Button className="dialog-box-button" onClick={yes}>Yes</Button>
        </ModalFooter>
      </ModalDialog>
    </div>
  )
}

DeleteDialog.propTypes = {
  image: PropTypes.object.isRequired
}

export default DeleteDialog

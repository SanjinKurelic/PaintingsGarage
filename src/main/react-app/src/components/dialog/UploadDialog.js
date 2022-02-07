import {useDispatch, useSelector} from 'react-redux'
import PropTypes from 'prop-types'
import Image from '../image/Image'
import {selectCurrentUser} from '../../redux/slice/currentUserSlice'
import {hideDialog} from '../../redux/slice/currentDialogSlice'

const UploadDialog = ({callback}) => {
  const dispatch = useDispatch()
  const currentUser = useSelector(selectCurrentUser)

  const image = {
    author: currentUser.user.name,
    uploaded: new Date()
  }

  const close = () => {
    dispatch(hideDialog(null))
    callback()
  }

  return (
    <Image image={image} closeCallback={close} editable={true} isNew={true}/>
  )
}

UploadDialog.propTypes = {
  callback: PropTypes.func.isRequired
}

export default UploadDialog

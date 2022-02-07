import {useDispatch} from 'react-redux'
import {hideDialog} from '../../redux/slice/currentDialogSlice'
import PropTypes from 'prop-types'
import Image from '../image/Image'

const EditDialog = ({image}) => {
  const dispatch = useDispatch()

  const cancel = () => {
    dispatch(hideDialog(null))
  }

  return (
    <Image image={image} closeCallback={cancel} editable={true} />
  )
}

EditDialog.propTypes = {
  image: PropTypes.object.isRequired
}

export default EditDialog

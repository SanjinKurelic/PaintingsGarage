import PropTypes from 'prop-types'
import {AiOutlineDelete, AiOutlineDollar, AiOutlineDownload, AiOutlineEdit} from 'react-icons/all'
import './imageActionButton.scss'
import {DialogType} from '../dialog/AllDialogs'

const ImageActionButton = ({type, size, callback}) => {
  switch (type) {
    case 'OWNER':
      return (
        <div>
          <AiOutlineDownload onClick={() => callback(DialogType.DOWNLOAD)} size={size} role="button"/>
          <AiOutlineEdit onClick={() => callback(DialogType.EDIT)} size={size} role="button"/>
          <AiOutlineDelete onClick={() => callback(DialogType.DELETE)} color="red" size={size} role="button"/>
        </div>
      )
    case 'BOUGHT':
      return <AiOutlineDownload onClick={() => callback(DialogType.DOWNLOAD)} size={size} role="button"/>
    default:
      return <AiOutlineDollar onClick={() => callback(DialogType.ADD_TO_CART)} size={size} role="button"/>
  }
}

ImageActionButton.propTypes = {
  type: PropTypes.oneOf(['OWNER', 'BOUGHT', 'NONE']),
  size: PropTypes.string,
  callback: PropTypes.func.isRequired
}

ImageActionButton.defaultProps = {
  type: 'NONE',
  size: '1.3em'
}

export default ImageActionButton

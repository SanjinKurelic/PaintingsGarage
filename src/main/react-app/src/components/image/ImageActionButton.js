import PropTypes from 'prop-types'
import {AiOutlineDelete, AiOutlineDollar, AiOutlineDownload, AiOutlineEdit} from 'react-icons/all'
import './imageActionButton.scss'

const ImageActionButton = ({type, size}) => {
  switch (type) {
    case 'OWNER':
      return (
        <div>
          <AiOutlineDownload size={size} role="button"/>
          <AiOutlineEdit size={size} role="button"/>
          <AiOutlineDelete color="red" size={size} role="button"/>
        </div>
      )
    case 'BOUGHT':
      return <AiOutlineDownload size={size} role="button"/>
    default:
      return <AiOutlineDollar size={size} role="button"/>
  }
}

ImageActionButton.propTypes = {
  type: PropTypes.oneOf(['OWNER', 'BOUGHT', 'NONE']),
  size: PropTypes.string
}

ImageActionButton.defaultProps = {
  type: 'NONE',
  size: '1.3em'
}

export default ImageActionButton
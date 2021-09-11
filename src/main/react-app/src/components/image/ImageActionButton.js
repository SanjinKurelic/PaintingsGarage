import PropTypes from 'prop-types'
import {AiOutlineDelete, AiOutlineDollar, AiOutlineDownload, AiOutlineEdit} from 'react-icons/all'

const ImageActionButton = ({type, size}) => {
  switch (type) {
    case 'owner':
      return (
        <div>
          <AiOutlineDownload size={size} role="button"/>
          <AiOutlineEdit size={size} role="button"/>
          <AiOutlineDelete color="red" size={size} role="button"/>
        </div>
      )
    case 'bought':
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
  size: '1.2em'
}

export default ImageActionButton
import PropTypes from 'prop-types'
import {AiOutlineDelete, AiOutlineDollar, AiOutlineDownload, AiOutlineEdit} from 'react-icons/all'


const ImageActionButton = ({type}) => {
  switch (type) {
    case 'owner':
      return (
        <div>
          <AiOutlineDownload size='1.2em' />
          <AiOutlineEdit size='1.2em' />
          <AiOutlineDelete color='red' size='1.2em' />
        </div>
      )
    case 'bought':
      return <AiOutlineDownload size='1.2em' />
    default:
      return <AiOutlineDollar size='1.2em' />
  }
}

ImageActionButton.propTypes = {
  type: PropTypes.oneOf(['owner','bought','none'])
}

ImageActionButton.defaultProps = {
  type: 'none'
}

export default ImageActionButton